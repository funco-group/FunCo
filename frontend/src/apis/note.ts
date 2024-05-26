import { NoteDetailType } from '@/interfaces/note/NoteDetailType'
import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import localAxios from '@/utils/http-commons'
import { AxiosResponse } from 'axios'

const domain = 'notes'

interface NotesReqBodyType {
  title: string
  content: string
  ticker: string
  thumbnailImage: string
}

interface NotesCommentBodyType {
  parentCommentId?: number
  content: string
}

export async function getNotesList(
  query: string,
  success: (res: AxiosResponse<NotePreviewType[]>) => void,
) {
  await localAxios.get(`/v1/${domain}?${query}`).then(success)
}

export async function postNotes(
  body: NotesReqBodyType,
  success: (res: AxiosResponse<{ noteId: number }>) => void,
) {
  await localAxios.post(`/v1/${domain}`, body).then(success)
}

export async function getNotesDetail(
  noteId: number,
  success: (res: AxiosResponse<NoteDetailType>) => void,
) {
  await localAxios.get(`/v1/${domain}/${noteId}`).then(success)
}

export async function updateNotes(
  noteId: number,
  body: NotesReqBodyType,
  success: () => void,
) {
  await localAxios.put(`/v1/${domain}/${noteId}`, body).then(success)
}

export async function deleteNotes(noteId: number, success: () => void) {
  await localAxios.delete(`/v1/${domain}/${noteId}`).then(success)
}

export async function getCommentsData(noteId: number) {
  const res = await fetch(
    `${process.env.NEXT_PUBLIC_BASE_URL}/v1/notes/${noteId}/comments`,
    {
      cache: 'no-store',
    },
  )
  return res.json()
}

export async function postNotesComment(
  noteId: number,
  body: NotesCommentBodyType,
  success: () => void,
) {
  await localAxios.post(`/v1/${domain}/${noteId}/comments`, body).then(success)
}

export async function updateComment(
  commentId: number,
  body: { content: string },
  success: () => void,
) {
  await localAxios.put(`/v1/comments/${commentId}`, body).then(success)
}

export async function deleteComment(commentId: number, success: () => {}) {
  await localAxios.delete(`/v1/comments/${commentId}`).then(success)
}

export async function postImage(
  formData: FormData,
  success: (res: AxiosResponse<{ url: string }>) => void,
) {
  await localAxios.post(`/v1/${domain}/image`, formData).then(success)
}

export async function postNoteLike(noteId: number, success: () => void) {
  await localAxios.post(`v1/${domain}/${noteId}/like`).then(success)
}
