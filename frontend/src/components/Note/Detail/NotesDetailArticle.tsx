'use client'

/* eslint-disable @next/next/no-img-element */
import noteParseDate from '@/utils/noteParseDate'
import useUserState from '@/hooks/recoilHooks/useUserState'
import { useRecoilValue } from 'recoil'
import { codeNameMapState } from '@/recoils/crypto'
import DummyNotesDetail from '@/lib/DummyNotesDetail'
import { useRouter } from 'next/navigation'
import ToastViewer from '@/components/Common/ToastUI/ToastViewer'
import NotesDetailArticleLikeBtn from './NotesDetailArticleLikeBtn'
import NotesDetailBtnDiv from './NotesDetailBtnDiv'

interface NotesDetailArticleProps {
  noteId: number
}

function NotesDetailArticle({ noteId }: NotesDetailArticleProps) {
  const router = useRouter()
  const coinMap = useRecoilValue(codeNameMapState)
  const detail = DummyNotesDetail[noteId - 1]

  const { user } = useUserState()

  const handleClickCoinBtn = () => {
    router.push(`/notes?coin=${detail.ticker}`)
  }

  const handleClickProfile = () => {
    router.push(`/member/${detail.member.memberId}`)
  }

  const handleModifyArticle = () => {
    console.log('modify Article')
  }

  const handleDeleteArticle = () => {
    console.log('delete Article')
  }

  return (
    <div>
      <div className="rounded border border-solid border-deactivatedGray bg-brandWhite p-3">
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
              {user?.memberId === detail.member.memberId ? (
                <div className="flex gap-1 text-brandDarkGray">
                  <span
                    className="cursor-pointer"
                    onClick={handleModifyArticle}
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
          <ToastViewer initialValue={detail.content} />
        </div>
        <div className="mb-5 mt-8 flex justify-center">
          <NotesDetailArticleLikeBtn
            initialIsLike={detail.liked}
            initialLikeCnt={detail.likeCount}
          />
        </div>
      </div>
      <NotesDetailBtnDiv />
      <h2>댓글 {detail.commentCount}개</h2>
    </div>
  )
}

export default NotesDetailArticle
