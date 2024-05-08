import { Dispatch, SetStateAction, useState } from 'react'
import { useRecoilValue } from 'recoil'
import { codeNameMapState } from '@/recoils/crypto'
import NotesFilterBtn from '../Notes/NotesFilterBtn'
import CoinFilterModal from '../Notes/CoinFilterModal'

interface CoinSelectProps {
  coinList: string[]
  setCoinList: Dispatch<SetStateAction<string[]>>
}

function CoinSelect({ coinList, setCoinList }: CoinSelectProps) {
  const [openModal, setOpenModal] = useState(false)
  const coinMap = useRecoilValue(codeNameMapState)

  const handleCoinBtn = () => {
    setOpenModal(true)
  }

  const handleCoinFilterBtn = (btnName: string) => {
    setCoinList((prev) => prev.filter((coin) => coin !== btnName))
  }

  return (
    <div>
      {openModal && (
        <CoinFilterModal
          setCoinList={setCoinList}
          setOpenModal={setOpenModal}
          coinList={coinList}
        />
      )}
      <div>
        <NotesFilterBtn
          content="가상 화폐 선택"
          active={coinList.length > 0}
          buttonName="COIN"
          handleBtn={handleCoinBtn}
        />
      </div>

      {coinList.length > 0 ? (
        <div className="mt-3">
          {coinList.map((coin) => (
            <button
              key={coin}
              type="button"
              className="my-1 mr-1 w-fit rounded border-none bg-brandColor p-1 text-xs text-brandWhite"
              onClick={() => handleCoinFilterBtn(coin)}
            >
              {coinMap.get(coin)}
            </button>
          ))}
        </div>
      ) : null}
    </div>
  )
}

export default CoinSelect
