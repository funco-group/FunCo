'use client'

import { StyledContainer } from '@/styles/CommonStyled'
import 'react-toastify/dist/ReactToastify.css'

interface ToastProviderProps {
  children: React.ReactNode
}

function ToastProvider({ children }: ToastProviderProps) {
  return (
    <>
      {children}
      <StyledContainer
        position="top-right"
        closeOnClick
        closeButton={false}
        pauseOnHover={false}
        pauseOnFocusLoss={false}
        theme="light"
        draggable
        draggablePercent={80}
      />
    </>
  )
}

export default ToastProvider
