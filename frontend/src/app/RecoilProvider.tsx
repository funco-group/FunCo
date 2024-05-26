'use client'

import { RecoilRoot } from 'recoil'

interface RecoilRootProviderProps {
  children: React.ReactNode
}

export default function RecoilRootProvider({
  children,
}: RecoilRootProviderProps) {
  return <RecoilRoot>{children}</RecoilRoot>
}
