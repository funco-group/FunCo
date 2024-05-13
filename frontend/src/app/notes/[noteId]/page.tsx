import NotesDetail from '@/containers/NotesContainer/DetailContainer'

function NotesDetailPage({ params }: { params: { noteId: number } }) {
  return <NotesDetail noteId={params.noteId} />
}

export default NotesDetailPage
