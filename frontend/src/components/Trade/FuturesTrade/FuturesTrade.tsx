import React, { useEffect, useState } from 'react'
import { useRecoilValue } from 'recoil'
import { userState } from '@/recoils/user'
import { getCash } from '@/apis/member'
import { AxiosResponse } from 'axios'
import { CashType } from '@/interfaces/common/AssetType'
import inputDecimalFormat from '@/utils/inputDecimalFormat'
import { usePathname } from 'next/navigation'
import { futuresLong, futuresShort, settlement } from '@/apis/trade'
import useLoginAlertModalState from '@/hooks/recoilHooks/useLoginAlertModalState'
import AlertModal from '@/components/Common/Modal/AlertModal'
import {
  AmountDiv,
  ButtonContainer,
  ContentDiv,
  PriceButton,
  PriceButtons,
  PriceInput,
  TitleDiv,
  TradeInnerContainer,
  TradeItem,
} from '@/styles/Trade.styled'
import { FuturesTradeButton } from './FuturesTrade.styled'

interface FuturesTradeProps {
  futureId: number
  isTrade: boolean
  setIsTrade: React.Dispatch<React.SetStateAction<boolean>>
}

function FuturesTrade({ futureId, isTrade, setIsTrade }: FuturesTradeProps) {
  const pathname = usePathname()
  const user = useRecoilValue(userState)

  const coinCode = pathname?.split('/')[3]
  const volumeButtons = [10, 20, 25, 30, 40, 50, 75, 100]
  const [buttons, setButtons] = useState<string[]>([])
  const [cash, setCash] = useState<number>(0) // 주문 가능
  const [leverage, setLeverage] = useState<number>(50)
  const [formatted, setFormatted] = useState<string>('')
  const [order, setOrder] = useState<number>(0) // 주문 금액
  const [alert, setAlert] = useState<boolean>(false)
  const [alertContent, setAlertContent] = useState<string>('')
  const { onLoginAlertModal } = useLoginAlertModalState()

  const getCashFunc = () => {
    getCash((response: AxiosResponse<CashType>) => {
      const { data } = response
      setCash(data.cash)
    })
  }

  const resetInput = () => {
    getCashFunc()
    setLeverage(50)
    setFormatted('')
    setOrder(0)
  }

  useEffect(() => {
    getCashFunc()
  }, [])

  useEffect(() => {
    setButtons(isTrade ? ['CLOSE'] : ['LONG', 'SHORT'])
  }, [isTrade])

  const clickVolumeButton = (rate: number) => {
    setOrder(Math.round(cash * rate * 0.01))
    setFormatted(Math.round(cash * rate * 0.01).toLocaleString('ko-KR'))
  }

  const closeAlert = () => {
    setAlert(false)
  }

  const successFunc = () => {
    setAlertContent('주문이 등록되었습니다.')
    setAlert(true)
    resetInput()
    setIsTrade(true)
  }

  const errorFunc = (response: any) => {
    if (response.data.errorCode === 'INSUFFICIENT_ASSET') {
      setAlertContent('자산이 충분하지 않습니다.')
    } else {
      setAlertContent('서버에 오류가 발생했습니다.')
    }
    setAlert(true)
  }

  const clickFuturesButton = (type: string) => {
    if (user.user) {
      if (cash < order) {
        setAlertContent('자산이 충분하지 않습니다.')
        setAlert(true)
        return
      }
      if (type === 'CLOSE') {
        settlement(futureId, () => {
          setAlertContent('정산 되었습니다.')
          setAlert(true)
          setIsTrade(false)
          resetInput()
        })
      } else if (type === 'LONG') {
        futuresLong(
          coinCode,
          order,
          leverage,
          () => {
            successFunc()
          },
          (response) => {
            errorFunc(response)
          },
        )
      } else {
        futuresShort(
          coinCode,
          order,
          leverage,
          () => {
            successFunc()
          },
          (response) => {
            errorFunc(response)
          },
        )
      }
    } else {
      onLoginAlertModal()
    }
  }

  return (
    <>
      {alert && (
        <AlertModal
          title="알림"
          content={alertContent}
          closeAlert={closeAlert}
        />
      )}
      <TradeInnerContainer>
        <div>
          <TradeItem>
            <TitleDiv>주문 가능</TitleDiv>
            <ContentDiv>
              {cash.toLocaleString('ko-KR')}
              <div>WON</div>
            </ContentDiv>
          </TradeItem>
          <TradeItem>
            <TitleDiv>레버리지</TitleDiv>
            <ContentDiv>
              <input
                type="range"
                value={leverage}
                min={1}
                onChange={(e) => {
                  setLeverage(e.target.valueAsNumber)
                }}
              />
              <div>{leverage} X</div>
            </ContentDiv>
          </TradeItem>
          <TradeItem>
            <TitleDiv>주문 금액</TitleDiv>
            <AmountDiv>
              <PriceInput
                value={formatted}
                placeholder="0"
                onChange={(e) =>
                  inputDecimalFormat(e, setFormatted, setOrder, '매수')
                }
              />
            </AmountDiv>
          </TradeItem>
          <TradeItem>
            <div />
            <PriceButtons>
              {volumeButtons.map((rate) => (
                <PriceButton key={rate} onClick={() => clickVolumeButton(rate)}>
                  {rate}%
                </PriceButton>
              ))}
            </PriceButtons>
          </TradeItem>
        </div>
      </TradeInnerContainer>

      <ButtonContainer>
        {buttons.map((button) => (
          <FuturesTradeButton
            key={button}
            onClick={() => clickFuturesButton(button)}
            name={button}
          >
            {button}
          </FuturesTradeButton>
        ))}
      </ButtonContainer>
    </>
  )
}

export default FuturesTrade
