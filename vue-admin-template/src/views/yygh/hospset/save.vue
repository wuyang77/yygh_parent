<template>
    <div class="app-container">
        <el-form label-width="120px">
            <el-form-item label="医院名称">
                <el-input v-model="hospset.hosname"/>
            </el-form-item>

            <el-form-item label="医院编号">
                <el-input v-model="hospset.hoscode"/>
            </el-form-item>

            <el-form-item label="api地址">
                <el-input v-model="hospset.apiUrl"/>
            </el-form-item>

            <el-form-item label="联系人">
                <el-input v-model="hospset.contactsName"/>
            </el-form-item>

            <el-form-item label="电话">
                <el-input v-model="hospset.contactsPhone"/>
            </el-form-item>

            <el-form-item>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import hospset from '@/api/yygh/hospset'

    export default{
        data(){//定义属性的初始值,对属性进行初始化
            return {
                hospset:{},
                saveBtnDisabled: false // 保存按钮是否禁用,
            }
        },
        methods:{

            // 根据id更新记录
            updateData() {
                this.saveBtnDisabled = true
                hospset.updateById(this.hospset).then(response => {
                    return this.$message({
                        type: 'success',
                        message: '修改成功!'
                    })
                }).then(resposne => {
                    this.$router.push({ path: '/yygh/hospset/list' })
                }).catch((response) => {
                    // console.log(response)
                    this.$message({
                        type: 'error',
                        message: '保存失败'
                    })
                })
            },
            saveData() {
                hospset.saveHospSet(this.hospset).then(response => {
                    return this.$message({
                        type: 'success',
                        message: '保存成功!'
                    })
                }).then(resposne => {
                    this.$router.push({ path: '/yygh/hospset/list' })
                }).catch((response) => {
                    // console.log(response)
                    this.$message({
                        type: 'error',
                        message: '保存失败'
                    })
                })
            },
            saveOrUpdate() {
                this.saveBtnDisabled = true
                if (!this.hospset.id) {
                    this.saveData();
                } else {
                    this.updateData();
                }
            },
            // 根据id查询医院的详情，item是后端绑定的key
            fetchDataById(id) {
                hospset.getById(id).then(response => {
                    this.hospset = response.data.item
                }).catch((response) => {
                    this.$message({
                        type: 'error',
                        message: '获取数据失败'
                    })
                })
            }
        },
        created(){
           console.log('created')
            if (this.$route.params && this.$route.params.id) {
            const id = this.$route.params.id
              this.fetchDataById(id)
            }
        }
    }
</script>
