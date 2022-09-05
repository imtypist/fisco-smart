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
 * @file InferencePrecompiled.h
 * @author: Junqin Huang
 * @date 2022-04-08
 */

#pragma once

#include "../../vm/Precompiled.h"
#include "bcos-executor/src/precompiled/common/Common.h"

namespace bcos
{
namespace precompiled
{
class InferencePrecompiled : public bcos::precompiled::Precompiled
{
public:
    using Ptr = std::shared_ptr<InferencePrecompiled>;
    InferencePrecompiled(crypto::Hash::Ptr _hashImpl);
    virtual ~InferencePrecompiled(){};

    std::shared_ptr<PrecompiledExecResult> call(
        std::shared_ptr<executor::TransactionExecutive> _executive, 
        PrecompiledExecResult::Ptr _callParameters) override;
};
}  // namespace precompiled
}  // namespace bcos
