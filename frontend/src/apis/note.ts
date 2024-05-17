import { NoteDetailType } from '@/interfaces/note/NoteDetailType'
import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import localAxios from '@/utils/http-commons'
import { AxiosResponse } from 'axios'

const domain = 'notes'

interface PostNotesReqBodyType {
  title: string
  content: string
  ticker: string
  thumbnailImage: string
}

export async function getNotesList(
  query: string,
  success: (res: AxiosResponse<NotePreviewType[]>) => void,
) {
  await localAxios
    .get(`/v1/${domain}?${query}`)
    .then(success)
    .catch((error) => console.error(error))
}

export async function postNotes(
  body: PostNotesReqBodyType,
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

export async function postImage(
  formData: FormData,
  success: (res: AxiosResponse<{ url: string }>) => void,
) {
  localAxios.post(`/v1/${domain}/image`, formData).then(success)
}
