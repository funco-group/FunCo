interface NoDataProps {
  content: string
  // eslint-disable-next-line react/require-default-props
  height?: number
}

function NoData({ content, height = 80 }: NoDataProps) {
  return (
    <div
      className={`flex  h-[${height}px] w-full items-center justify-center text-[#999999]`}
    >
      <div>{content}</div>
    </div>
  )
}

export default NoData
