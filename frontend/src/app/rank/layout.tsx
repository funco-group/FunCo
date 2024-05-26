import NarrowLayout from '@/components/layout/NarrowLayout'

function RankLayout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <NarrowLayout>{children}</NarrowLayout>
    </div>
  )
}

export default RankLayout
