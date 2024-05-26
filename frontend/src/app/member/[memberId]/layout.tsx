import NarrowLayout from '@/components/layout/NarrowLayout'

function UserPageLayout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <NarrowLayout>{children}</NarrowLayout>
    </div>
  )
}

export default UserPageLayout
