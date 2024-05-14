'use client'

import LikeSVG from '@/../public/icon/like.svg'
import { useEffect, useState } from 'react'

interface NotesDetailArticleLikeBtnProps {
  initialIsLike: boolean
  initialLikeCnt: number
}

function NotesDetailArticleLikeBtn({
  initialIsLike,
  initialLikeCnt,
}: NotesDetailArticleLikeBtnProps) {
  const [isLike, setIsLike] = useState(initialIsLike)
  const [likeCnt, setLikeCnt] = useState(
    initialIsLike ? initialLikeCnt - 1 : initialLikeCnt + 1,
  )

  const handleIsLikeBtn = () => {
    setIsLike((prev) => !prev)
  }

  useEffect(() => {
    setLikeCnt((prev) => (isLike ? prev + 1 : prev - 1))
  }, [isLike])
  return (
    // eslint-disable-next-line jsx-a11y/click-events-have-key-events, jsx-a11y/no-static-element-interactions
    <div
      className="flex cursor-pointer select-none items-center gap-2 rounded-xl border border-solid px-6 py-2 outline-none"
      onClick={handleIsLikeBtn}
    >
      <div className="mt-2">
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
