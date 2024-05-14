export interface NoteCommentType {
  commentId: number
  member: {
    memberId: number
    nickname: string
    profileUrl: string
  }
  childComments?: NoteCommentType[]
  content: string
  date: string
}
