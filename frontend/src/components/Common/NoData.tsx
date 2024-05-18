interface NoDataProps {
  content: string
  // eslint-disable-next-line react/require-default-props
  height?: number
}

function NoData({ content, height = 20 }: NoDataProps) {
  const heightClass = `h-${height}`
  return (
    <div
      className={`flex ${heightClass} w-full items-center justify-center text-[#999999]`}
    >
      <div>{content}</div>
    </div>
  )
}

export default NoData
