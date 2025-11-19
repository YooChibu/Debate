/**
 * 관리자 통계 서비스
 */

import api from './api'

export const getUserStatistics = async () => {
  const response = await api.get('/admin/statistics/users')
  return response
}

export const getDebateStatistics = async () => {
  const response = await api.get('/admin/statistics/debates')
  return response
}

export const getDailyUserRegistrations = async (days = 7) => {
  const response = await api.get('/admin/statistics/users/daily', {
    params: { days }
  })
  return response
}

export const getDailyDebateCreations = async (days = 7) => {
  const response = await api.get('/admin/statistics/debates/daily', {
    params: { days }
  })
  return response
}

export const adminStatisticsService = {
  getUserStatistics,
  getDebateStatistics,
  getDailyUserRegistrations,
  getDailyDebateCreations
}

