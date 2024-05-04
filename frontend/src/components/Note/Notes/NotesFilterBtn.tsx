interface NotesFilterBtnProps {
  content: string
  active: boolean
  buttonName: string
  handleBtn: (content: string) => void
}

function NotesFilterBtn({
  content,
  active,
  buttonName,
  handleBtn,
}: NotesFilterBtnProps) {
  const activeClasses = active
    ? 'border-brandColor border-2 text-brandColor'
    : 'border-deactivatedGray border text-brandDarkGray'
  return (
    <button
      type="button"
      className={`rounded-md ${activeClasses} h-9 w-20 cursor-pointer border-solid bg-brandWhite text-sm outline-none`}
      onClick={() => handleBtn(buttonName)}
    >
      {content}
    </button>
  )
}

export default NotesFilterBtn
