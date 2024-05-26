import { useState } from 'react'
import TradeButton from '@/components/Common/Trade/TradeButtonTab'
import { ButtonContainer } from '@/styles/Trade.styled'
import { userState } from '@/recoils/user'
import { useRecoilValue } from 'recoil'
import useLoginAlertModalState from '@/hooks/recoilHooks/useLoginAlertModalState'
import ShortTradeItem from './ShortTradeItem'

interface ShortTradeProps {
  curPrice: number
  getCurPrice: () => void
}

function ShortTrade({ curPrice, getCurPrice }: ShortTradeProps) {
  const buttons = ['매수', '매도']
  const [activeButton, setActiveButton] = useState<string>('매수')
  const user = useRecoilValue(userState)
  const { onLoginAlertModal } = useLoginAlertModalState()

  const changeButton = (button: string) => {
    if (!user.user) {
      onLoginAlertModal()
    }
    setActiveButton(button)
    getCurPrice()
  }

  return (
    <div>
      <ButtonContainer>
        {buttons.map((button) => (
          <TradeButton
            key={button}
            name={button}
            activeButton={activeButton}
            changeButton={changeButton}
          />
        ))}
      </ButtonContainer>
      {activeButton === '매수' ? (
        <ShortTradeItem name="매수" curPrice={curPrice} />
      ) : (
        <ShortTradeItem name="매도" curPrice={curPrice} />
      )}
    </div>
  )
}

export default ShortTrade
