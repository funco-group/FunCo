import { Dispatch, SetStateAction, useState } from 'react'
import { useRecoilValue } from 'recoil'
import { codeNameMapState } from '@/recoils/crypto'
import NotesFilterBtn from '../Notes/NotesFilterBtn'
import CoinSelectModal from './CoinSelectModal'

interface CoinSelectProps {
  coin: string
  setCoin: Dispatch<SetStateAction<string>>
}

function CoinSelect({ coin, setCoin }: CoinSelectProps) {
  const [openModal, setOpenModal] = useState(false)
  const coinMap = useRecoilValue(codeNameMapState)

  const handleCoinBtn = () => {
    setOpenModal(true)
  }

  return (
    <div>
      {openModal && (
        <CoinSelectModal
          setCoin={setCoin}
          setOpenModal={setOpenModal}
          coin={coin}
        />
      )}
      <div>
        <NotesFilterBtn
          content="가상 화폐 선택"
          active={coin.length > 0}
          buttonName="COIN"
          handleBtn={handleCoinBtn}
        />
      </div>

      {coin.length > 0 ? (
        <div className="mt-3">
          <button
            key={coin}
            type="button"
            className="my-1 mr-1 w-fit rounded border-none bg-brandColor p-1 text-xs text-brandWhite"
          >
            {coinMap.get(coin)}
          </button>
        </div>
      ) : null}
    </div>
  )
}

export default CoinSelect
