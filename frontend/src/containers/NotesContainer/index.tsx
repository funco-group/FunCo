'use client'

import TabButton from '@/components/Common/Button/TabButton.styled'
import NotePreviewList from '@/components/Note/Notes/NotePreviewList'
import NotesFilterBtnList from '@/components/Note/Notes/NotesFilterBtnList'
import NotesSearch from '@/components/Note/Notes/NotesSearch'
import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import getNotePreviewList from '@/lib/getNotesPreviewList'
import { useRouter, useSearchParams } from 'next/navigation'
import { useEffect, useState } from 'react'

function Notes() {
  const [nowFilter, setNowFilter] = useState<string>('')
  const [coinList, setCoinList] = useState<string[]>([])
  const [search, setSearch] = useState<string>('')
  const [keyword, setKeyword] = useState<string>('')
  const [sorted, setSorted] = useState<string>('')
  const [params, setParams] = useState<string[]>([])
  const [notePreviewList, setNotePreviewList] = useState<NotePreviewType[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const router = useRouter()
  const searchParams = useSearchParams()

  const sortedList: [string, string, 'left' | 'right' | ''][] = [
    ['최신 순', 'LATEST', 'left'],
    ['좋아요 순', 'RECOMMENDED', 'right'],
  ]

  const handleTabClick = (tabName: string) => {
    setSorted(tabName)
  }

  const handleWriteClick = () => {
    router.push('/note/write')
  }

  useEffect(() => {
    setIsLoading(true)
    setNowFilter(searchParams.get('type') || 'ALL')
    setCoinList(searchParams.get('coin')?.split(',') || [])
    setSearch(searchParams.get('search') || '')
    setKeyword(searchParams.get('keyword') || '')
    setSorted(searchParams.get('sorted') || 'LATEST')

    const apiParams = [
      searchParams.get('type') ? `type=${searchParams.get('type')}` : '',
      searchParams.get('coin') ? `coin=${searchParams.get('coin')}` : '',
      searchParams.get('search') ? `search=${searchParams.get('search')}` : '',
      searchParams.get('keyword')
        ? `keyword=${searchParams.get('keyword')}`
        : '',
      searchParams.get('sorted') ? `sorted=${searchParams.get('sorted')}` : '',
    ].filter(Boolean)

    console.log('api 호출', apiParams)
    setNotePreviewList(getNotePreviewList(`/notes?${apiParams.join('&')}`))
    setIsLoading(false)
  }, [searchParams])

  useEffect(() => {
    setParams(
      [
        nowFilter ? `type=${nowFilter}` : '',
        coinList.length > 0 ? `coin=${coinList.join(',')}` : '',
        search ? `search=${search}` : '',
        keyword ? `keyword=${keyword}` : '',
        sorted ? `sorted=${sorted}` : '',
      ].filter(Boolean),
    )
  }, [nowFilter, coinList, search, keyword, sorted])

  useEffect(() => {
    router.push(`/notes?${params.join('&')}`)
  }, [params])

  useEffect(() => {
    setIsLoading(false)
  }, [notePreviewList])

  if (isLoading) {
    // eslint-disable-next-line react/jsx-no-useless-fragment
    return <></>
  }

  return (
    <>
      <div className="flex justify-between">
        <NotesFilterBtnList
          nowFilter={nowFilter}
          setNowFilter={setNowFilter}
          coinList={coinList}
          setCoinList={setCoinList}
        />
        <NotesSearch setSearch={setSearch} setKeyword={setKeyword} />
      </div>
      <div className="mt-5 flex justify-between">
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
          className="block h-9 rounded border-none bg-brandColor px-4 font-NSB text-xs text-brandWhite"
          onClick={handleWriteClick}
        >
          글 작성
        </button>
      </div>
      <NotePreviewList notePreviewList={notePreviewList} />
    </>
  )
}

export default Notes
