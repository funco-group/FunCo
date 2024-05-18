'use client'

/* eslint-disable @next/next/no-img-element */
import noteParseDate from '@/utils/noteParseDate'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { useRecoilValue } from 'recoil'
import { codeNameMapState } from '@/recoils/crypto'
import { useRouter, useSearchParams } from 'next/navigation'
import ToastViewer from '@/components/Common/ToastUI/ToastViewer'
import { useEffect, useState } from 'react'
import { NoteDetailType } from '@/interfaces/note/NoteDetailType'
import { deleteNotes, getNotesDetail } from '@/apis/note'
import NoData from '@/components/Common/NoData'
import { useResizeDetector } from 'react-resize-detector'
import AlertWithCancelModal from '@/components/Common/Modal/AlertWithCancelModal'
import NotesDetailArticleLikeBtn from './NotesDetailArticleLikeBtn'
import NotesDetailBtnDiv from './NotesDetailBtnDiv'

interface NotesDetailArticleProps {
  noteId: number
}

function NotesDetailArticle({ noteId }: NotesDetailArticleProps) {
  const router = useRouter()
  const searchParams = useSearchParams()
  const coinMap = useRecoilValue(codeNameMapState)
  const [detail, setDetail] = useState<NoteDetailType>()
  const { user } = useUserState()
  const { height, ref } = useResizeDetector()
  const [onDeleteModal, setOnDeleteModal] = useState(false)

  useEffect(() => {
    getNotesDetail(noteId, (res) => {
      const { data } = res
      setDetail(data)
    })
  }, [noteId])

  useEffect(() => {
    if (!detail) return

    const scroll = searchParams.get('scroll')

    if (scroll) {
      if (typeof window !== 'undefined') {
        const scrollPoint = document.getElementById(`${scroll}`)?.offsetTop
        if (scrollPoint !== undefined) {
          window.scroll({
            top: scrollPoint - 140,
            behavior: 'smooth',
          })
        }
      }
    }
  }, [detail, height])

  if (!detail) {
    return <NoData content="Loading..." />
  }

  const handleClickCoinBtn = () => {
    router.push(`/notes?coin=${detail.ticker}`)
  }

  const handleClickProfile = () => {
    router.push(`/member/${detail.member.id}`)
  }

  const handleUpdateArticle = () => {
    router.push(`/notes/write?noteId=${noteId}`)
  }

  const handleDeleteArticle = () => {
    setOnDeleteModal(true)
  }

  return (
    <div>
      {onDeleteModal && (
        <AlertWithCancelModal
          title="알림"
          content="게시글을 삭제하시겠습니까?"
          cancelAlert={() => {
            setOnDeleteModal(false)
          }}
          confirmAlert={() => {
            deleteNotes(noteId, () => {
              setOnDeleteModal(false)
              router.push('/notes')
            })
          }}
        />
      )}
      <div
        className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3"
        ref={ref}
      >
        <div
          className="w-fit cursor-pointer rounded bg-brandColor p-1 text-sm text-brandWhite"
          onClick={handleClickCoinBtn}
        >
          {coinMap.get(detail.ticker)}
        </div>
        <h2>{detail.title}</h2>
        <div className="flex">
          <img
            src={detail.member.profileUrl}
            className="block h-12 w-12 cursor-pointer rounded-full"
            alt="profile"
            onClick={handleClickProfile}
          />
          <div className="ml-2 flex flex-col justify-center text-xs">
            <div
              className="cursor-pointer font-NSB text-base"
              onClick={handleClickProfile}
            >
              {detail.member.nickname}
            </div>
            <div className="flex gap-2">
              <div className="text-brandDarkGray">
                {noteParseDate(detail.writeDate)}
              </div>
              {user?.memberId === detail.member.id ? (
                <div className="flex gap-1 text-brandDarkGray">
                  <span
                    className="cursor-pointer"
                    onClick={handleUpdateArticle}
                  >
                    수정
                  </span>
                  <span
                    className="cursor-pointer"
                    onClick={handleDeleteArticle}
                  >
                    삭제
                  </span>
                </div>
              ) : null}
            </div>
          </div>
        </div>
        <div className="my-3">
          <ToastViewer key={detail.content} initialValue={detail.content} />
        </div>
        <div className="mb-5 mt-8 flex justify-center">
          <NotesDetailArticleLikeBtn
            noteId={noteId}
            initialIsLike={detail.liked}
            initialLikeCnt={detail.likeCount}
          />
        </div>
      </div>
      <NotesDetailBtnDiv />
    </div>
  )
}

export default NotesDetailArticle
