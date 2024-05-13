import { Dispatch, SetStateAction, useState } from 'react'
import { codeNameMapState } from '@/recoils/crypto'
import { useRecoilValue } from 'recoil'
import NotesFilterBtn from './NotesFilterBtn'
import CoinFilterModal from './CoinFilterModal'

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
  const [openModal, setOpenModal] = useState(false)
  const coinMap = useRecoilValue(codeNameMapState)

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
    setOpenModal(true)
  }

  const handleCoinFilterBtn = (btnName: string) => {
    setCoinList((prev) => prev.filter((coin) => coin !== btnName))
  }

  return (
    <div className="min-h-[76px]">
      {openModal && (
        <CoinFilterModal
          setCoinList={setCoinList}
          setOpenModal={setOpenModal}
          coinList={coinList}
        />
      )}
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
      <div className="mt-2">
        {coinList.length > 0
          ? coinList.map((coin) => (
              <button
                key={coin}
                type="button"
                className="my-1 mr-1 w-fit rounded border-none bg-brandColor p-1 text-xs text-brandWhite"
                onClick={() => handleCoinFilterBtn(coin)}
              >
                {coinMap.get(coin)}
              </button>
            ))
          : null}
      </div>
    </div>
  )
}

export default NotesFilterBtnList
