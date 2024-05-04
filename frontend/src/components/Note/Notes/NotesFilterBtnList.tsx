import { Dispatch, SetStateAction } from 'react'
import NotesFilterBtn from './NotesFilterBtn'

interface NotesFilterBtnListProps {
  nowFilter: string
  setNowFilter: Dispatch<SetStateAction<string>>
  coinList: string[]
  setCoinList: Dispatch<SetStateAction<string[]>>
}

function NotesFilterBtnList({
  nowFilter,
  setNowFilter,
  coinList,
  setCoinList,
}: NotesFilterBtnListProps) {
  const buttonList = [
    ['전체', 'ALL'],
    ['작성한 글', 'MY'],
    ['좋아요 글', 'LIKE'],
  ]

  const handleBtn = (btnName: string) => {
    setNowFilter(btnName)
  }

  const handleCoinBtn = () => {
    // 모달 오픈
    console.log('모달 오픈')
    if (coinList.length) {
      setCoinList([])
    } else {
      setCoinList(['비트코인', '이더리움'])
    }
  }

  return (
    <div>
      <div className="flex gap-2">
        {buttonList.map((button) => (
          <NotesFilterBtn
            content={button[0]}
            buttonName={button[1]}
            active={nowFilter === button[1]}
            handleBtn={handleBtn}
            key={button[0]}
          />
        ))}
        <NotesFilterBtn
          content="가상 화폐"
          active={coinList.length > 0}
          buttonName="COIN"
          handleBtn={handleCoinBtn}
        />
      </div>
      {coinList.length > 0 ? (
        <p className="font-NSB text-brandColor my-1 text-sm">
          코인 필터 : {coinList.join(' ')}
        </p>
      ) : null}
    </div>
  )
}

export default NotesFilterBtnList
