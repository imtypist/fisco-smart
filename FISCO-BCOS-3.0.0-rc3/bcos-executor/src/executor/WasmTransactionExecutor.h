/*
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
 * @brief WasmTransactionExecutor
 * @file WasmTransactionExecutor.h
 * @author: jimmyshi
 * @date: 2022-01-18
 */
#pragma once

#include "TransactionExecutor.h"

#include <utility>

namespace bcos
{
namespace executor
{
class WasmTransactionExecutor : public TransactionExecutor
{
public:
    using Ptr = std::shared_ptr<WasmTransactionExecutor>;
    using ConstPtr = std::shared_ptr<const WasmTransactionExecutor>;

    WasmTransactionExecutor(txpool::TxPoolInterface::Ptr txpool,
        storage::MergeableStorageInterface::Ptr cachedStorage,
        storage::TransactionalStorageInterface::Ptr backendStorage,
        protocol::ExecutionMessageFactory::Ptr executionMessageFactory,
        bcos::crypto::Hash::Ptr hashImpl, bool isAuthCheck)
      : TransactionExecutor(std::move(txpool), std::move(cachedStorage), std::move(backendStorage),
            std::move(executionMessageFactory), std::move(hashImpl), isAuthCheck)
    {
        initPrecompiled();
        assert(!m_constantPrecompiled->empty());
        assert(m_builtInPrecompiled);
        m_isWasm = true;
    }

    ~WasmTransactionExecutor() override = default;

private:
    void initPrecompiled();

    std::shared_ptr<BlockContext> createBlockContext(
        const protocol::BlockHeader::ConstPtr& currentHeader,
        storage::StateStorage::Ptr tableFactory,
        storage::StorageInterface::Ptr lastStorage) override;

    std::shared_ptr<BlockContext> createBlockContext(bcos::protocol::BlockNumber blockNumber,
        h256 blockHash, uint64_t timestamp, int32_t blockVersion,
        storage::StateStorage::Ptr tableFactory) override;
};

}  // namespace executor
}  // namespace bcos
