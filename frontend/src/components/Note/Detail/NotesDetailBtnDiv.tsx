'use client'

import { useRouter } from 'next/navigation'

function NotesDetailBtnDiv() {
  const router = useRouter()

  const handleRouteList = () => {
    router.push('/notes')
  }

  const handleScrollToTop = () => {
    if (typeof window !== 'undefined') {
      window.scrollTo({ top: 0, behavior: 'smooth' })
    }
  }

  const buttonClasses =
    'block h-9 rounded border-none bg-brandColor px-4 font-NSB text-xs text-brandWhite'
  return (
    <div className="mt-3 flex justify-end gap-2">
      <button
        type="button"
        className={`${buttonClasses}`}
        onClick={handleRouteList}
      >
        목록
      </button>
      <button
        type="button"
        className={`${buttonClasses}`}
        onClick={handleScrollToTop}
      >
        TOP
      </button>
    </div>
  )
}

export default NotesDetailBtnDiv
