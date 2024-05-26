'use client'

import LikeSVG from '@/../public/icon/like.svg'
import { postNoteLike } from '@/apis/note'
import { useEffect, useState } from 'react'

interface NotesDetailArticleLikeBtnProps {
  noteId: number
  initialIsLike: boolean
  initialLikeCnt: number
}

function NotesDetailArticleLikeBtn({
  noteId,
  initialIsLike,
  initialLikeCnt,
}: NotesDetailArticleLikeBtnProps) {
  const [isLike, setIsLike] = useState(initialIsLike)
  const [likeCnt, setLikeCnt] = useState(
    initialIsLike ? initialLikeCnt - 1 : initialLikeCnt + 1,
  )

  const handleIsLikeBtn = () => {
    postNoteLike(noteId, () => {
      setIsLike((prev) => !prev)
    })
  }

  useEffect(() => {
    setLikeCnt((prev) => (isLike ? prev + 1 : prev - 1))
  }, [isLike])

  // useEffect(
  //   () => () => {
  //     console.log(isLike, likeCnt)
  //   },
  //   [],
  // )
  return (
    <div
      className="flex cursor-pointer select-none items-center gap-2 rounded-xl border border-solid px-6 py-2 outline-none"
      onClick={handleIsLikeBtn}
    >
      <div className="mt-1">
        {isLike ? (
          <LikeSVG fill="red" />
        ) : (
          <LikeSVG fill="transparent" stroke="black" />
        )}
      </div>
      <div className="font-NSB text-2xl">{likeCnt}</div>
    </div>
  )
}

export default NotesDetailArticleLikeBtn
