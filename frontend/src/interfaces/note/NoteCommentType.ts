export interface NoteCommentType {
  commentId: number
  member?: {
    id: number
    nickname: string
    profileUrl: string
  }
  childComments?: NoteCommentType[]
  content: string
  date: string
}
