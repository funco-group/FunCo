import React, { useEffect, useState } from 'react'
import {
  SettleModalBackgroundContainer,
  SettleModalContainer,
  SettleModalContentButtonRowDiv,
  SettleModalTitleDiv,
} from '../TradeHistory/Follow/Following/SettleModal.styled'
import { ContentDiv, ModalItemDiv, TitleDiv } from './BuyPortfolioModal.styled'
import BrandButtonComponent from '../Common/Button/BrandButtonComponent'
import { InputDiv } from './FollowModal.styled'
import { setPortfolioPrivate, setPortfolioPublic } from '@/apis/member'
import AlertModal from '../Common/Modal/AlertModal'

interface SetPortfolioModalProps {
  handleSettingPortfolio: () => void
  portfolioStatus: string
  portfolioPrice: number
}

function SetPortfolioModal({
  handleSettingPortfolio,
  portfolioStatus,
  portfolioPrice,
}: SetPortfolioModalProps) {
  const [status, setStatus] = useState<string>('')
  const [price, setPrice] = useState<number>(0)
  const [alert, setAlert] = useState<boolean>(false)

  useEffect(() => {
    setPrice(portfolioPrice)
    setStatus(status)
  }, [portfolioPrice, portfolioStatus])

  const setPortfolio = () => {
    if (price > 0) {
      if (status === 'public') {
        setPortfolioPublic(status, () => {
          handleAlert()
        })
      } else {
        setPortfolioPrivate(status, price, () => {
          handleAlert()
        })
      }
    }
  }

  const handleAlert = () => {
    setAlert(!alert)
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
          <TitleDiv>· 공개</TitleDiv>
          <ContentDiv>토글</ContentDiv>
        </ModalItemDiv>
        <ModalItemDiv>
          <TitleDiv>· 가격</TitleDiv>
          <ContentDiv>
            <InputDiv>
              <input
                type="text"
                value={price}
                onChange={(e) => {
                  setPrice(Number(e.target.value))
                }}
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
            onClick={setPortfolio}
            disabled={false}
          />
        </SettleModalContentButtonRowDiv>
      </SettleModalContainer>
    </SettleModalBackgroundContainer>
  )
}

export default SetPortfolioModal
