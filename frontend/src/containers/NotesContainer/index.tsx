'use client'

import NotesFilterBtnList from '@/components/Note/Notes/NotesFilterBtnList'
import NotesSearch from '@/components/Note/Notes/NotesSearch'
import { useState } from 'react'

function Notes() {
  const [nowFilter, setNowFilter] = useState('ALL')
  const [coinList, setCoinList] = useState<string[]>([])
  const [search, setSearch] = useState<string>('')
  const [keyword, setKeyword] = useState<string>('')

  return (
    <div>
      <div className="flex justify-between">
        <NotesFilterBtnList
          nowFilter={nowFilter}
          setNowFilter={setNowFilter}
          coinList={coinList}
          setCoinList={setCoinList}
        />
        <NotesSearch
          search={search}
          setSearch={setSearch}
          keyword={keyword}
          setKeyword={setKeyword}
        />
      </div>
      <div></div>
      <div></div>
    </div>
  )
}

export default Notes
