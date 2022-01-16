import cryptoJs from 'crypto-js'

/** 随机生成的10个加密密码 */
const encrypt = (pw, pl) => {
  let p = ''
  switch (pl) {
    case 0x00:
      p = '6c72eb20b0270b21'
      break
    case 0x01:
      p = '9770cd5f69af4c3f'
      break
    case 0x02:
      p = '5475a79972e00ab9'
      break
    case 0x03:
      p = '1bce2a8d8d4cdee6'
      break
    case 0x04:
      p = 'eb19eca22327bbea'
      break
    case 0x05:
      p = '846d58ca0b04b2ca'
      break
    case 0x06:
      p = '6730d305996c98f4'
      break
    case 0x07:
      p = 'd499b685e28f149f'
      break
    case 0x08:
      p = 'cf40d0cc0330fa77'
      break
    case 0x09:
      p = 'c1febab486fbb3f4'
      break
  }
  /** aes加密 */
  return cryptoJs.AES.encrypt(pw, cryptoJs.enc.Utf8.parse(p), { mode: cryptoJs.mode.ECB }).toString()
}

export default encrypt
