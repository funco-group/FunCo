import NotesDetailArticle from '@/components/Note/Detail/NotesDetailArticle'

interface NotesDetailProps {
  noteId: number
}

function NotesDetail({ noteId }: NotesDetailProps) {
  return (
    <div>
      <NotesDetailArticle noteId={noteId} />
    </div>
  )
}

export default NotesDetail
