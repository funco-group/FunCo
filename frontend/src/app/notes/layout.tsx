import WideLayout from '@/components/layout/WideLayout'

function NotesLayout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <WideLayout>{children}</WideLayout>
    </div>
  )
}

export default NotesLayout
