
module.exports = (obj, success = true, msg = "成功") => {
  return {
    code: 0,
    msg,
    success,
    data: obj
  }
}