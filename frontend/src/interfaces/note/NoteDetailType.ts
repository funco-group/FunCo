export interface NoteDetailType {
  noteId: number
  member: {
    id: number
    nickname: string
    profileUrl: string
  }
  title: string
  content: string
  ticker: string
  writeDate: string
  likeCount: number
  liked: boolean
}
