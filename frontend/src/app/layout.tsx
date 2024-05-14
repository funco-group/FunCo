import type { Metadata } from 'next'
import './globals.css'
import StyledComponentsRegistry from '@/lib/registry'
import Navbar from '@/containers/HeaderContainer'
import RecoilRootProvider from './RecoilProvider'
import ToastProvider from './ToastProvider'
import RecoilSetter from './RecoilSetter'
import ScrollToTop from '@/styles/ScrollToTop'
import GlobalStyle from './_global'

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
      <header>
        <link
          rel="stylesheet"
          href="https://hangeul.pstatic.net/hangeul_static/css/nanum-square.css"
        />
      </header>
      <body>
        <RecoilRootProvider>
          <RecoilSetter>
            <ToastProvider>
              <StyledComponentsRegistry>
                <Navbar />
                <ScrollToTop />
                <GlobalStyle />
                <div style={{ padding: '6.5rem 0' }}>{children}</div>
              </StyledComponentsRegistry>
            </ToastProvider>
          </RecoilSetter>
        </RecoilRootProvider>
      </body>
    </html>
  )
}
