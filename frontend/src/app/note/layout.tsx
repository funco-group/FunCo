import WideLayout from '@/components/layout/WideLayout'

function NoteLayout({ children }: { children: React.ReactNode }) {
  return (
    <div>
      <WideLayout>{children}</WideLayout>
    </div>
  )
}

export default NoteLayout
