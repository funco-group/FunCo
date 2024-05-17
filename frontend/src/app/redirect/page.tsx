'use client'

import { useEffect } from 'react'
import { postGoogleOAuth } from '@/apis/auth'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { useRouter } from 'next/navigation'

function Redirect() {
  const router = useRouter()
  const { login } = useUserState()

  useEffect(() => {
    // 클라이언트 사이드에서만 실행되도록 window 객체를 확인
    if (typeof window !== 'undefined') {
      const queryParams = new URLSearchParams(window.location.search)
      const code = queryParams.get('code')

      if (code) {
        postGoogleOAuth((res) => {
          const { data } = res
          login(data) // Recoil 상태 업데이트
          router.push('/') // 사용자를 홈 페이지로 리디렉트
        }, code)
      }
    }
  }, [])

  return <div>redirect</div>
}

export default Redirect
