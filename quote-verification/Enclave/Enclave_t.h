#ifndef ENCLAVE_T_H__
#define ENCLAVE_T_H__

#include <stdint.h>
#include <wchar.h>
#include <stddef.h>
#include "sgx_edger8r.h" /* for sgx_ocall etc. */

#include "sgx_qve_header.h"
#include "sgx_ql_quote.h"

#include <stdlib.h> /* for size_t */

#define SGX_CAST(type, item) ((type)(item))

#ifdef __cplusplus
extern "C" {
#endif

sgx_status_t ecall_get_target_info(sgx_target_info_t* target_info);
quote3_error_t sgx_tvl_verify_qve_report_and_identity(const uint8_t* p_quote, uint32_t quote_size, const sgx_ql_qe_report_info_t* p_qve_report_info, time_t expiration_check_date, uint32_t collateral_expiration_status, sgx_ql_qv_result_t quote_verification_result, const uint8_t* p_supplemental_data, uint32_t supplemental_data_size, sgx_isv_svn_t qve_isvsvn_threshold);

sgx_status_t SGX_CDECL sgx_oc_cpuidex(int cpuinfo[4], int leaf, int subleaf);
sgx_status_t SGX_CDECL sgx_thread_wait_untrusted_event_ocall(int* retval, const void* self);
sgx_status_t SGX_CDECL sgx_thread_set_untrusted_event_ocall(int* retval, const void* waiter);
sgx_status_t SGX_CDECL sgx_thread_setwait_untrusted_events_ocall(int* retval, const void* waiter, const void* self);
sgx_status_t SGX_CDECL sgx_thread_set_multiple_untrusted_events_ocall(int* retval, const void** waiters, size_t total);

#ifdef __cplusplus
}
#endif /* __cplusplus */

#endif
