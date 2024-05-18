import React, { useState } from 'react'
import { setPortfolio } from '@/apis/member'
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
  SettleModalTitleDiv,
} from '../TradeHistory/Follow/Following/SettleModal.styled'
import { ContentDiv, ModalItemDiv, TitleDiv } from './BuyPortfolioModal.styled'
import BrandButtonComponent from '../Common/Button/BrandButtonComponent'
import { InputDiv } from './FollowModal.styled'
import AlertModal from '../Common/Modal/AlertModal'
import ToggleButton from './ToggleButton'

interface SetPortfolioModalProps {
  handleSettingPortfolio: () => void
  status: boolean
  setStatus: React.Dispatch<React.SetStateAction<boolean>>
  price: number
  setPrice: React.Dispatch<React.SetStateAction<number>>
  priceStr: string
  setPriceStr: React.Dispatch<React.SetStateAction<string>>
}

function SetPortfolioModal({
  handleSettingPortfolio,
  status,
  setStatus,
  price,
  setPrice,
  priceStr,
  setPriceStr,
}: SetPortfolioModalProps) {
  const [alert, setAlert] = useState<boolean>(false)

  const setPortfolioFuc = async () => {
    if (price > 0) {
      await setPortfolio(
        status ? 'private' : 'public',
        status ? price : 0,
        () => {
          setAlert(true)

          if (!status) {
            setPrice(0)
            setPriceStr('0')
          }
        },
      )
    }
  }

  const handleInvestmentInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newPrice = e.target.value
    const newPriceNum = Number(newPrice.replace(/,/g, ''))

    if (Number.isNaN(newPriceNum)) {
      setPrice(0)
      setPriceStr('0')
    }
    setPrice(newPriceNum)
    setPriceStr(newPriceNum.toLocaleString('ko-KR'))
  }

  const handleAlert = () => {
    setAlert(!alert)
    handleSettingPortfolio()
  }

  const clickToggle = () => {
    setStatus(!status)
  }

  return (
    <SettleModalBackgroundContainer>
      {alert && (
        <AlertModal
          title="알림"
          content="설정이 완료되었습니다."
          closeAlert={handleAlert}
        />
      )}

      <SettleModalContainer width="27rem">
        <SettleModalTitleDiv>포트폴리오 설정</SettleModalTitleDiv>
        <ModalItemDiv>
          <TitleDiv>· 유료화</TitleDiv>
          <ContentDiv>
            <ToggleButton
              width="50px"
              height="30px"
              isToggleOn={status}
              handleClick={clickToggle}
            />
          </ContentDiv>
        </ModalItemDiv>
        <ModalItemDiv>
          <TitleDiv>· 가격</TitleDiv>
          <ContentDiv>
            <InputDiv>
              <input
                type="text"
                value={priceStr}
                onChange={handleInvestmentInput}
                disabled={!status}
              />
              <span>WON</span>
            </InputDiv>
          </ContentDiv>
        </ModalItemDiv>
        <SettleModalContentButtonRowDiv>
          <BrandButtonComponent
            content="취소"
            color={null}
            cancel
            onClick={handleSettingPortfolio}
            disabled={false}
          />
          <BrandButtonComponent
            content="확인"
            color={null}
            cancel={false}
            onClick={setPortfolioFuc}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  )
}

export default SetPortfolioModal
