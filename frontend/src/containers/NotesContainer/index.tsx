'use client'

import TabButton from '@/components/Common/Button/TabButton.styled'
import NotesFilterBtnList from '@/components/Note/Notes/NotesFilterBtnList'
import NotesSearch from '@/components/Note/Notes/NotesSearch'
import { useRouter } from 'next/navigation'
import { useState } from 'react'

function Notes() {
  const [nowFilter, setNowFilter] = useState('ALL')
  const [coinList, setCoinList] = useState<string[]>([])
  const [search, setSearch] = useState<string>('')
  const [keyword, setKeyword] = useState<string>('')
  const [sorted, setSorted] = useState<string>('LATEST')
  const router = useRouter()

  const sortedList: [string, string, 'left' | 'right' | ''][] = [
    ['최신 순', 'LATEST', 'left'],
    ['추천 순', 'RECOMMENDED', 'right'],
  ]

  const handleTabClick = (tabName: string) => {
    setSorted(tabName)
  }

  const handleWriteClick = () => {
    router.push('/note/write')
  }

  return (
    <div>
      <div className="flex justify-between">
        <NotesFilterBtnList
          nowFilter={nowFilter}
          setNowFilter={setNowFilter}
          coinList={coinList}
          setCoinList={setCoinList}
        />
        <NotesSearch setSearch={setSearch} setKeyword={setKeyword} />
      </div>
      <div className="mt-3 flex justify-between">
        <div>
          {sortedList.map((item) => (
            <TabButton
              width={null}
              height="35px"
              $active={item[1] === sorted}
              radius={item[2]}
              onClick={() => handleTabClick(item[1])}
              key={item[1]}
            >
              {item[0]}
            </TabButton>
          ))}
        </div>
        <button
          type="button"
          className="block h-9 cursor-pointer rounded border-none bg-brandColor px-4 font-NSB text-xs text-brandWhite"
          onClick={handleWriteClick}
        >
          글 작성
        </button>
      </div>
      <div className="grid grid-cols-3 gap-8">
        <div className="h-11 bg-black" />
        <div className="h-11 bg-black" />
        <div className="h-11 bg-black" />
        <div className="h-11 bg-black" />
        <div className="h-11 bg-black" />
        <div className="h-11 bg-black" />
        <div className="h-11 bg-black" />
      </div>
    </div>
  )
}

export default Notes
