/**
 * 관리자 토론 관리 서비스
 * 
 * 토론 조회, 수정, 삭제, 숨김 처리 등 토론 관리 API를 호출하는 서비스입니다.
 */

import api from './api'

/**
 * 토론 목록 조회
 */
export const getDebates = async (params = {}) => {
  const { keyword, status, isHidden, page = 0, size = 20 } = params
  const response = await api.get('/admin/debate', {
    params: { keyword, status, isHidden, page, size }
  })
  return response
}

/**
 * 토론 상세 조회
 */
export const getDebateDetail = async (id) => {
  const response = await api.get(`/admin/debate/${id}`)
  return response
}

/**
 * 토론 수정
 */
export const updateDebate = async (id, data) => {
  const { title, content, startDate, endDate } = data
  const response = await api.put(`/admin/debate/${id}`, null, {
    params: { title, content, startDate, endDate }
  })
  return response
}

/**
 * 토론 상태 변경
 */
export const updateDebateStatus = async (id, status) => {
  const response = await api.put(`/admin/debate/${id}/status`, null, {
    params: { status }
  })
  return response
}

/**
 * 토론 숨김 처리
 */
export const toggleDebateHidden = async (id) => {
  const response = await api.put(`/admin/debate/${id}/toggle-hidden`)
  return response
}

/**
 * 토론 삭제
 */
export const deleteDebate = async (id) => {
  const response = await api.delete(`/admin/debate/${id}`)
  return response
}

export const adminDebateService = {
  getDebates,
  getDebateDetail,
  updateDebate,
  updateDebateStatus,
  toggleDebateHidden,
  deleteDebate
}

