'use client'

import { getNotesList } from '@/apis/note'
import TabButton from '@/components/Common/Button/TabButton.styled'
import NoData from '@/components/Common/NoData'
import NotePreviewList from '@/components/Note/Notes/NotePreviewList'
import NotesSearch from '@/components/Note/Notes/NotesSearch'
import { NotePreviewType } from '@/interfaces/note/NotePreviewType'
import dynamic from 'next/dynamic'
import { useRouter, useSearchParams } from 'next/navigation'
import { useEffect, useState, useRef, useCallback } from 'react'

const NotesFilterBtnList = dynamic(
  () => import('@/components/Note/Notes/NotesFilterBtnList'),
  { ssr: false },
)

function Notes() {
  const [nowFilter, setNowFilter] = useState<string>('ALL')
  const [coinList, setCoinList] = useState<string[]>([])
  const [search, setSearch] = useState<string>('')
  const [keyword, setKeyword] = useState<string>('')
  const [sorted, setSorted] = useState<string>('LATEST')
  const [params, setParams] = useState<string[]>([])
  const [notePreviewList, setNotePreviewList] = useState<NotePreviewType[]>([])
  const [isLoading, setIsLoading] = useState(false)
  const [page, setPage] = useState(0)
  const [hasMore, setHasMore] = useState(true)

  const observer = useRef<IntersectionObserver | null>(null)
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
    router.push('/notes/write')
  }

  useEffect(() => {
    setIsLoading(true)
    setNowFilter(searchParams.get('type') || 'ALL')
    setCoinList(searchParams.get('coin')?.split(',') || [])
    setSearch(searchParams.get('search') || '')
    setKeyword(searchParams.get('keyword') || '')
    setSorted(searchParams.get('sorted') || 'LATEST')

    const apiParams = [
      searchParams.get('type')
        ? `type=${searchParams.get('type')}`
        : 'type=ALL',
      searchParams.get('coin') ? `coin=${searchParams.get('coin')}` : '',
      searchParams.get('search') ? `search=${searchParams.get('search')}` : '',
      searchParams.get('keyword')
        ? `keyword=${searchParams.get('keyword')}`
        : '',
      searchParams.get('sorted')
        ? `sorted=${searchParams.get('sorted')}`
        : 'sorted=LATEST',
      'page=0',
      'size=9',
    ].filter(Boolean)

    getNotesList(apiParams.join('&'), (res) => {
      const { data } = res
      setNotePreviewList(data)
      setIsLoading(false)
      setPage(1)
      setHasMore(data.length > 0)
    })
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

  const lastNoteElementRef = useCallback(
    (node: HTMLElement | null) => {
      if (isLoading) return
      if (observer.current) observer.current.disconnect()
      observer.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasMore) {
          setIsLoading(true)
          const apiParams = [
            `type=${nowFilter}`,
            coinList.length > 0 ? `coin=${coinList.join(',')}` : '',
            search ? `search=${search}` : '',
            keyword ? `keyword=${keyword}` : '',
            `sorted=${sorted}`,
            `page=${page}`,
            'size=9',
          ].filter(Boolean)

          getNotesList(apiParams.join('&'), (res) => {
            const { data } = res
            setNotePreviewList((prev) => [...prev, ...data])
            setIsLoading(false)
            setPage((prevPage) => prevPage + 1)
            setHasMore(data.length > 0)
          })
        }
      })
      if (node) observer.current.observe(node)
    },
    [isLoading, hasMore, nowFilter, coinList, search, keyword, sorted, page],
  )

  const getNotePreviewListDiv = () => {
    if (notePreviewList.length === 0 && !isLoading) {
      return <NoData content="게시글이 없습니다." height={80} />
    }
    return (
      <NotePreviewList
        notePreviewList={notePreviewList}
        setCoinList={setCoinList}
        lastNoteElementRef={lastNoteElementRef}
      />
    )
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
      {getNotePreviewListDiv()}
      {isLoading && <NoData content="Loading..." height={20} />}
    </>
  )
}

export default Notes
