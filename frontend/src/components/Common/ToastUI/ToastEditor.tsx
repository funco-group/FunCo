import { ThumbnailImageType } from '@/interfaces/note/ThumbnailImageType'
import { Editor } from '@toast-ui/react-editor'
import { Dispatch, RefObject, SetStateAction } from 'react'

const toolbarItems = [
  ['heading', 'bold', 'italic', 'strike'],
  ['hr'],
  ['ul', 'ol', 'task'],
  ['table', 'link'],
  ['image'],
  ['scrollSync'],
]

interface ToastEditorProps {
  editorRef: RefObject<Editor>
  setImageList: Dispatch<SetStateAction<ThumbnailImageType[]>>
}

function ToastEditor({ editorRef, setImageList }: ToastEditorProps) {
  const onUploadImage = (file: File, callback: typeof Function) => {
    setImageList((prev) => {
      if (prev.length > 0) {
        return [
          ...prev,
          { src: '/image/chuu.gif', thumbnail: false, onMouse: false },
        ]
      }
      return [{ src: '/image/chuu.gif', thumbnail: true, onMouse: false }]
    })
    callback('/image/chuu.gif')
  }

  return (
    <div className="mt-4">
      {editorRef && (
        <Editor
          ref={editorRef}
          initialValue=""
          initialEditType="markdown"
          previewStyle="vertical"
          hideModeSwitch
          height="55vh"
          theme=""
          usageStatistics={false}
          toolbarItems={toolbarItems}
          useCommandShortcut
          hooks={{ addImageBlobHook: onUploadImage }}
        />
      )}
    </div>
  )
}

export default ToastEditor
