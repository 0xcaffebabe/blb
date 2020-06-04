const BASE_URL = "http://localhost:8001"
async function request(params) {  
                //返回一个promise实例。  
                return await new Promise((resolve, reject) => {
					const token = uni.getStorageSync("token")
                    uni.request({  
                        url: BASE_URL+"/"+params.url,
						method: params.method,
                        data: params.data,
						header: {"TOKEN": token, "TYPE":"RIDER","content-type":"application/json"},
                        success: (result) => {
                            resolve(result)
                        },  
                        fail: (e) => {  
                            reject(e);  
                        }  
                    })  
                })  
            }
class Repository {
	async login (username, password) {
		const data = await request({
			url: `/login?username=${username}&password=${password}`,
			method: "POST"
		})
		return data.data
	}
	async register (params) {
		const data = await request({
			url: '/register',
			data: params,
			method: 'POST'
		})
		return data.data
	}
	async getDeliveryOrder () {
		const data = await request({
			url: '/order/delivery',
			method:'GET'
		})
		return data.data
	}
	async grabOrder (orderId) {
		const data = await request({
			url: '/order/grab/' + orderId,
			method: 'POST'
		})
		return data.data
	}
	async getUndeliveryOrder(){
		const data = await request({
			url: '/order/undelivery',
			method:'GET'
		})
		return data.data
	}
	async getOrderDetail(orderId){
		const data = await request({
			url: '/order/detail/'+orderId,
			method:'GET'
		})
		return data.data
	}
	async shipOrder (orderId) {
		const data = await request({
			url: `/order/${orderId}/delivery`,
			method:'POST'
		})
		return data.data
	}
	async completeOrder (orderId,code) {
		const data = await request({
			url: `/order/${orderId}/complete?code=${code}`,
			method:'POST'
		})
		return data.data
	}
}
export default new Repository()