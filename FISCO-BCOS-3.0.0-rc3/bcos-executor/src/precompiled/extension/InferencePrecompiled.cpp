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
#include "../PrecompiledResult.h"
#include "../Utilities.h"
#include <fstream>

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

std::string InferencePrecompiled::toString()
{
    return "Inference";
}

std::shared_ptr<PrecompiledExecResult> InferencePrecompiled::call(
    std::shared_ptr<executor::TransactionExecutive> _executive, bytesConstRef _param,
    const std::string&, const std::string&, int64_t)
{
    PRECOMPILED_LOG(TRACE) << LOG_BADGE("InferencePrecompiled") << LOG_DESC("call")
                           << LOG_KV("param", toHexString(_param));

    // parse function name
    uint32_t func = getParamFunc(_param);
    bytesConstRef data = getParamData(_param);
    auto blockContext = _executive->blockContext().lock();
    auto codec =
        std::make_shared<PrecompiledCodec>(blockContext->hashHandler(), blockContext->isWasm());
    auto callResult = std::make_shared<PrecompiledExecResult>();
    auto gasPricer = m_precompiledGasFactory->createPrecompiledGas();
    gasPricer->setMemUsed(_param.size());
    
    if (func == name2Selector[INFERENCE_METHOD_PREDICT])
    {   // predict() function call
        // default retMsg
        std::string retValue = "Call Inference!";

        // std::string execString = "cd /home/junqin/gramine/examples/pytorch;gramine-direct pytorch pytorchexample.py";
        std::string resPath = "/home/junqin/gramine/examples/pytorch/result.txt";
        std::string cmd;
        codec->decode(data, cmd);
        int retCode = system(cmd.c_str());
        if (retCode != -1 and retCode != 127)
        {
            std::ifstream ifs;
            ifs.open(resPath);
            if (ifs.is_open())
            {
                getline(ifs, retValue);    
            }
            PRECOMPILED_LOG(ERROR) << LOG_BADGE("InferencePrecompiled") << LOG_DESC("predict")
                                   << LOG_KV("result", retValue);
        }
        callResult->setExecResult(codec->encode(retValue));
    }
    else
    {  // unknown function call
        PRECOMPILED_LOG(ERROR) << LOG_BADGE("InferencePrecompiled")
                               << LOG_DESC(" unknown function ") << LOG_KV("func", func);
        callResult->setExecResult(codec->encode(u256((int)CODE_UNKNOW_FUNCTION_CALL)));
    }
    gasPricer->updateMemUsed(callResult->m_execResult.size());
    callResult->setGas(gasPricer->calTotalGas());
    return callResult;
}
