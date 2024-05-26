'use client'

import '@toast-ui/editor/dist/toastui-editor.css'
import { Viewer } from '@toast-ui/react-editor'

interface ToastViewerProps {
  initialValue: string
}

function ToastViewer({ initialValue }: ToastViewerProps) {
  return <Viewer initialValue={initialValue} />
}

export default ToastViewer
