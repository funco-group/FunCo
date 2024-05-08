import { Editor } from '@toast-ui/react-editor'
import { useRef } from 'react'

const toolbarItems = [
  ['heading', 'bold', 'italic', 'strike'],
  ['hr'],
  ['ul', 'ol', 'task'],
  ['table', 'link'],
  ['image'],
  ['scrollSync'],
]

function ToastEditor() {
  const editorRef = useRef<Editor>(null)
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
          // hooks={{ addImageBlobHook: onUploadImage }}
        />
      )}
    </div>
  )
}

export default ToastEditor
