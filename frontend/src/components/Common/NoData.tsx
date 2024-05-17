interface NoDataProps {
  content: string
}

function NoData({ content }: NoDataProps) {
  return (
    <div className="flex h-20 w-full items-center justify-center text-[#999999]">
      <div>{content}</div>
    </div>
  )
}

export default NoData
