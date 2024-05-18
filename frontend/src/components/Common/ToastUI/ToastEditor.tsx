import { ThumbnailImageType } from '@/interfaces/note/ThumbnailImageType'
import { Editor } from '@toast-ui/react-editor'
import { Dispatch, RefObject, SetStateAction } from 'react'
import '@toast-ui/editor/dist/toastui-editor.css'
import { postImage } from '@/apis/note'

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
  imageList: ThumbnailImageType[]
  setImageList: Dispatch<SetStateAction<ThumbnailImageType[]>>
}

function ToastEditor({ editorRef, imageList, setImageList }: ToastEditorProps) {
  const onUploadImage = (file: File, callback: typeof Function) => {
    const formData = new FormData()
    formData.append('file', file)

    postImage(formData, (res) => {
      const { data } = res
      console.log(data)
      setImageList((prev) => [
        ...prev,
        {
          src: data.url,
          thumbnail: prev.length === 0,
          onMouse: false,
        },
      ])
      callback(data.url)
    })
  }

  const handleChangeEditor = () => {
    const currentMarkdown = editorRef.current?.getInstance().getMarkdown()

    const urlsInEditor = Array.from(
      currentMarkdown.matchAll(/!\[.*?\]\((.*?)\)/g),
      (m: RegExpMatchArray) => m[1],
    )

    // 이미지 리스트 정렬 및 동기화
    const existingImageMap = new Map(
      imageList.map((image) => [image.src, image]),
    )

    const newImageList = urlsInEditor.map((url) => {
      const existingImage = existingImageMap.get(url)
      return (
        existingImage ?? {
          src: url,
          thumbnail: false,
          onMouse: false,
        }
      )
    })

    if (
      newImageList.length > 0 &&
      newImageList.filter((image) => image.thumbnail).length === 0
    ) {
      newImageList[0].thumbnail = true
    }

    setImageList(newImageList)
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
          onChange={handleChangeEditor}
        />
      )}
    </div>
  )
}

export default ToastEditor