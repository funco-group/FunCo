import type { Metadata } from 'next'
import './globals.css'
import StyledComponentsRegistry from '@/lib/registry'
import Navbar from '@/containers/HeaderContainer'
import ScrollToTop from '@/styles/ScrollToTop'
import RecoilRootProvider from './RecoilProvider'
import ToastProvider from './ToastProvider'
import RecoilSetter from './RecoilSetter'

export const metadata: Metadata = {
  title: 'FUNCO',
  description: '즐겁게 배우는 가상화폐 모의 투자 플랫폼',
  icons: {
    icon: '/favicon.png',
  },
}

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  return (
    <html lang="en">
      <head>
        <link
          rel="stylesheet"
          href="https://hangeul.pstatic.net/hangeul_static/css/nanum-square.css"
        />
      </head>
      <body>
        <RecoilRootProvider>
          <RecoilSetter>
            <ToastProvider>
              <StyledComponentsRegistry>
                <Navbar />
                <ScrollToTop />
                <div style={{ padding: '6.5rem 0' }}>{children}</div>
              </StyledComponentsRegistry>
            </ToastProvider>
          </RecoilSetter>
        </RecoilRootProvider>
      </body>
    </html>
  )
}
