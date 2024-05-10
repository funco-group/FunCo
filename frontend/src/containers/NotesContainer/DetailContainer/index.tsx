interface NotesDetailProps {
  noteId: number
}
function NotesDetail({ noteId }: NotesDetailProps) {
  return <div>{noteId}</div>
}

export default NotesDetail
