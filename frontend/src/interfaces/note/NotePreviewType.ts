export interface NotePreviewType {
  noteId: number
  member: {
    memberId: number
    nickname: string
    profileUrl: string
  }
  thumbnailImage: string
  thumbnailContent: string
  title: string
  ticker: string
  writeDate: string
  likeCount: number
  liked: boolean
  commentCount: number
}
