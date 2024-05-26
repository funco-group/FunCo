interface NoDataProps {
  content: string
  height: number | string
}

function NoData({ content, height }: NoDataProps) {
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
