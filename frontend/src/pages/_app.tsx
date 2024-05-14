import '@/app/globals.css';  // 전역 CSS 파일을 임포트합니다.
import type { AppProps } from 'next/app';

function MyApp({ Component, pageProps }: AppProps) {
  return <Component {...pageProps} />;
}

export default MyApp;