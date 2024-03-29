/**
 *  Copyright (C) 2021 FISCO BCOS.
 *  SPDX-License-Identifier: Apache-2.0
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * @file InferencePrecompiled.cpp
 * @author: Junqin Huang
 * @date 2022-04-08
 */

#include "InferencePrecompiled.h"
#include "../../executive/BlockContext.h"
#include "bcos-executor/src/precompiled/common/PrecompiledResult.h"
#include "bcos-executor/src/precompiled/common/Utilities.h"
#include <fstream>
#include <sys/timeb.h>
#include <iostream>

using namespace bcos;
using namespace bcos::executor;
using namespace bcos::storage;
using namespace bcos::precompiled;

// predict interface
const char* const INFERENCE_METHOD_PREDICT = "predict(string)";

InferencePrecompiled::InferencePrecompiled(crypto::Hash::Ptr _hashImpl) : Precompiled(_hashImpl)
{
    name2Selector[INFERENCE_METHOD_PREDICT] = getFuncSelector(INFERENCE_METHOD_PREDICT, _hashImpl);
}

std::shared_ptr<PrecompiledExecResult> InferencePrecompiled::call(
    std::shared_ptr<executor::TransactionExecutive> _executive, 
    PrecompiledExecResult::Ptr _callParameters)
{
    timeb t;
    std::string cur_ts;
    ftime(&t);
    cur_ts = std::to_string(t.time*1000 + t.millitm);
    std::cout << "[SMART][CPP][" << cur_ts << "] switch to off-chain execution" << std::endl;

    PRECOMPILED_LOG(TRACE) << LOG_BADGE("InferencePrecompiled") << LOG_DESC("call")
                           << LOG_KV("param", toHexString(_callParameters->input()));

    // parse function name
    uint32_t func = getParamFunc(_callParameters->input());
    bytesConstRef data = _callParameters->params();
    auto blockContext = _executive->blockContext().lock();
    auto codec = CodecWrapper(blockContext->hashHandler(), blockContext->isWasm());
    auto gasPricer = m_precompiledGasFactory->createPrecompiledGas();
    gasPricer->setMemUsed(_callParameters->input().size());
    
    if (func == name2Selector[INFERENCE_METHOD_PREDICT])
    {   // predict() function call
        std::string retValue = "retCode: ";

        std::string resPath = "/home/junqin/fisco-smart/tee-provider/result.txt";
        std::string cmd;
        codec.decode(data, cmd);
        
        ftime(&t);
        cur_ts = std::to_string(t.time*1000 + t.millitm);
        std::cout << "[SMART][CPP][" << cur_ts << "] outsource to TEE" << std::endl;

        int retCode = system(cmd.c_str());

        if ((retCode != -1) && WIFEXITED(retCode) && (WEXITSTATUS(retCode) == 0))
        {
            std::ifstream ifs;
            ifs.open(resPath);
            if (ifs.is_open())
            {
                getline(ifs, retValue);    
            }
        }else
        {
            retValue = retValue + std::to_string(retCode);
        }
        PRECOMPILED_LOG(TRACE) << LOG_BADGE("InferencePrecompiled") << LOG_DESC("predict")
                                   << LOG_KV("result", retValue);
        _callParameters->setExecResult(codec.encode(retValue));

        ftime(&t);
        cur_ts = std::to_string(t.time*1000 + t.millitm);
        std::cout << "[SMART][CPP][" << cur_ts << "] receive results from TEE" << std::endl;
    }
    else
    {  // unknown function call
        PRECOMPILED_LOG(ERROR) << LOG_BADGE("InferencePrecompiled")
                               << LOG_DESC(" unknown function ") << LOG_KV("func", func);
        _callParameters->setExecResult(codec.encode(u256((int)CODE_UNKNOW_FUNCTION_CALL)));
    }
    gasPricer->updateMemUsed(_callParameters->m_execResult.size());
    _callParameters->setGas(_callParameters->m_gas - gasPricer->calTotalGas());

    ftime(&t);
    cur_ts = std::to_string(t.time*1000 + t.millitm);
    std::cout << "[SMART][CPP][" << cur_ts << "] leave off-chain execution" << std::endl;

    return _callParameters;
}
